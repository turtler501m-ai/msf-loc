<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search" ></div>
<!-- 그리드영역 -->
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
    var startDate = '${srchStrtDt}';
    var endDate = '${srchEndDt}';
    
    var DHX = {
        // 검색
        FORM1 : {
            items : [
                { type : "settings", position : "label-left", labelAlign : "left", labelWidth : 70, blockOffset : 0 }
                , { type : "block", list : [
                    {type:"calendar", name:"srchStrtDt", label:"개통일자", value:startDate, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", labelWidth: 60, required: true, width:100, offsetLeft:10, readonly: true}
                    , {type:"newcolumn"}
                    , {type:"calendar", name:"srchEndDt", label:"~", value:endDate, labelWidth:20, labelAlign:"center", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", width:100, readonly: true}
                    , {type:"newcolumn"}
                    , {type:"input", width:115, label:"이벤트코드", name:"srchEventCd", offsetLeft:20, maxlength:"20"}
                    , {type:"newcolumn"}
                ]}
                , { type: "hidden", name: "DWNLD_RSN", value:""}, //엑셀다운로드 사유
                , { type: "hidden", name: "btnCount1", value: "0"}       // 조회 버튼 다중클릭 방지
                , { type: "hidden", name: "btnExcelCount1", value: "0"}  // 다운로드 버튼 다중클릭 방지
                , { type: "button",name : "btnSearch",value : "조회", className:"btn-search1" }
            ],
            onValidateMore : function(data){
                var form = this;

                if(data.srchStrtDt.trim() == ""){
                    this.pushError("srchStrtDt", "개통일자", "개통시작일자를 입력해주세요.");
                }

                if(data.srchEndDt.trim() == ""){
                    this.pushError("srchEndDt", "개통일자", "개통종료일자를 입력해주세요.");
                }

                if (data.srchStrtDt > data.srchEndDt) {
                    this.pushError("srchStrtDt", "개통일자", "시작일자가 종료일자보다 클 수 없습니다.");
                }

                form.setItemValue("btnCount1", 0);      // 초기화
                form.setItemValue("btnExcelCount1", 0); // 초기화
            },
            onButtonClick : function(btnId) {

                var form = this;

                switch (btnId) {
                    case "btnSearch":

                        var btnCount2 = parseInt(form.getItemValue("btnCount1", "")) + 1;
                        form.setItemValue("btnCount1", btnCount2);
                        if (form.getItemValue("btnCount1", "") != "1") return;

                        PAGE.GRID1.list(ROOT + "/org/prmtmgmt/getEventCodePrmtList.do", form, {
                            callback: function () {
                                form.resetValidateCss();
                            }
                        });
                        break;
                }
            }
        },
        GRID1 : {
            css : {
                height : "600px"
            },
            title : "참여이력",
            header : "이벤트명,이벤트코드,계약번호,고객명,휴대폰번호,최초요금제,현재요금제,개통일자,이동전통신사,추천인정보",    //10
            columnIds :"evntNm,evntCd,contractNum,subLinkName,subscriberNo,fstRateNm,lstRateNm,lstComActvDate,moveCompanyNm,recommendInfo", //10
            widths :"150,100,100,150,100,200,200,100,150,150",  //10
            colAlign :"left,center,center,left,center,left,left,center,center,center",  //10
            colTypes :"ro,ro,ro,ro,roXp,ro,ro,roXd,ro,ro",  //10
            colSorting :"str,str,str,str,str,str,str,str,str,str",  //10
            paging : true,
            pageSize : 20,
            showPagingAreaOnInit : true,
            buttons : {
                hright : {
                    btnExcel: { label : "자료생성"}
                }
            },
            onButtonClick : function(btnId) {

                var grid = this;

                switch (btnId) {
                    case "btnExcel":

                        if(grid.getRowsNum() == 0) {
                            mvno.ui.alert("데이터가 존재하지 않습니다.");
                            return;
                        }

                        var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
                        PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
                        if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록

                        mvno.cmn.downloadCallback(function(result) {
                            PAGE.FORM1.setItemValue("DWNLD_RSN",result);
                            mvno.cmn.ajax(ROOT + "/org/prmtmgmt/getEventCodePrmtListExcelDown.json?menuId=MSP2002910", PAGE.FORM1.getFormData(true), function(result){
                                mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
                            });
                        });

                        PAGE.FORM1.setItemValue("btnCount1", 0);      // 초기화
                        PAGE.FORM1.setItemValue("btnExcelCount1", 0); // 초기화

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
        }
    };
</script>