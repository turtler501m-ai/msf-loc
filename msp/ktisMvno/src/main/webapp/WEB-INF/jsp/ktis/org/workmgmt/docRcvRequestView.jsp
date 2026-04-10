<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : docRcvRequestView.jsp
   * @Description : 온라인 URL 문자발송 폼
   * @
   * @ 수정일	    수정자 수정내용
   * @ ---------- ------ -----------------------------
   * @ 2025.09.02      최초생성
   * @Create Date : 2025.09.02
   */
%>

<div class="section">
  <div class="section-header">
    <h2>업무정보</h2>
  </div>
</div>
<div id="FORM1" class="section-search"></div>

<div id="GRID1"></div>

<div id="GRID2"></div>

<div class="section">
  <div class="section-header">
    <h2>문자템플릿 상세</h2>
  </div>
</div>
<div id="FORM2" class="section-box"></div>

<div id="FORM3"></div>

<!-- Script -->
<script type="text/javascript">

    var resNo = "${resNo}";
    var maskedMobileNo = "${maskedMobileNo}";
    var viewType = "${viewType}";

    var date = new Date();
    var today = date.format("yyyymmdd");
    var defaultExpireDate = new Date(date.setDate(date.getDate() + 3));

    var DHX = {
        FORM1 : {
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}
                , {type:"block", list:[
                        {type:"select", label:"업무유형", name:"workId", width:150, offsetLeft:10}
                        , {type:"newcolumn"}
                        , {type:"input", label:"업무명", name:"workTmplNm", width:350, offsetLeft:60}
                    ]}
                , {type:"block", list:[
                        {type:"select", label:"요청자", name:"rqstrId", width:150, offsetLeft:10}
                        , {type:"newcolumn"}
                        , {type:"input", label:"수취서류", name:"itemNm", width:350, offsetLeft:60}
                    ]}
                , {type : "button",name : "btnSearch", value : "조회", className:"btn-search2"}
                , {type : "hidden", name : "searchBaseDate"}
                , {type : "hidden", name : "useYn", value : "Y"}
            ]
            ,onButtonClick : function(btnId) {
                var form = this;
                switch (btnId) {
                    case "btnSearch" :
                        PAGE.GRID1.clearAll();
                        PAGE.GRID1.list(ROOT + "/org/workMgmt/getWorkTmplList.json", form);

                        PAGE.GRID2.clearAll();

                        PAGE.FORM2.clear();
                        PAGE.FORM2.disableItem("btnDetail");
                        mvno.ui.disableButton("FORM2", "btnSend");
                        break;
                }
            }
        },
        GRID1 : {
            css : {
                height : "200px"
            },
            title : "업무목록",
            header : "유형코드,업무명,업무유형,명의자구분,요청자구분,서류수,등록자,등록일",
            columnIds : "workTmplId,workTmplNm,workNm,ownerNm,rqstrNm,docCnt,cretNm,cretDt",
            widths : "100,250,80,125,125,50,100,100",
            colAlign : "center,left,center,left,left,center,center,center",
            colTypes : "ro,ro,ro,ro,ro,ron,ro,roXd",
            colSorting : "str,str,str,str,str,int,str,str",
            paging : true,
            showPagingAreaOnInit : true,
            onRowSelect : function(rowId, celInd) {
                var rowData = this.getRowData(rowId);
                PAGE.GRID2.clearAll();
                PAGE.GRID2.list(ROOT + "/org/workMgmt/getWorkSmsListByWorkTmplId.json", {workTmplId : rowData.workTmplId});

                PAGE.FORM2.clear();
                PAGE.FORM2.disableItem("btnDetail");
                mvno.ui.disableButton("FORM2", "btnSend");
            }
        },
        GRID2 : {
            css : {
                height : "120px"
            },
            title : "문자템플릿",
            header : "유형코드,템플릿ID,템플릿명,문자제목,발신자번호",
            columnIds : "workTmplId,smsTemplateId,smsTemplateNm,subject,callback",
            widths : "100,70,250,490,120",
            colAlign : "center,right,left,left,center",
            colTypes : "ro,ron,ro,ro,ro",
            colSorting : "str,int,str,str,str",
            paging : false,
            hiddenColumns : [0],
            onRowSelect : function(rowId, celInd) {
                var rowData = this.getRowData(rowId);

                PAGE.FORM2.clear();
                PAGE.FORM2.setFormData(rowData);
                PAGE.FORM2.setItemValue("mobileNo", maskedMobileNo);
                PAGE.FORM2.setItemValue("expireDt", defaultExpireDate);
                PAGE.FORM2.enableItem("btnDetail");
                mvno.ui.enableButton("FORM2", "btnSend");
            }
        },
        FORM2 : {
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}
                , {type:"block", list:[
                        {type:"input", label:"템플릿ID", name:"smsTemplateId", width:100, offsetLeft:10, disabled:true, style:"text-align:right;"}
                        , {type:"newcolumn"}
                        , {type:"input", label:"발신자번호", name:"callback", labelWidth:64, width:100, offsetLeft:46, disabled:true}
                        , {type:"newcolumn"}
                        , {type:"input", label:"수신번호", name:"mobileNo", width:100, offsetLeft:46}
                        , {type:"newcolumn"}
                        , {type:"calendar", label:"만료일자", name:"expireDt", width:100, offsetLeft:46, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", readonly: true}
                    ]}
                , {type:"block", list:[
                        {type:"input", label:"템플릿명", name:"smsTemplateNm", width:730, offsetLeft:10, disabled:true}
                        , {type:"newcolumn"}
                        , {type:"button", value:"문자내용", name:"btnDetail", width:80, offsetLeft:2}
                        , {type:"hidden", value:"문자내용", name:"smsText"}
                    ]}
            ]
            , buttons : {
                center : {
                    btnSend : {label : "문자전송"}
                    , btnClose : {label : "닫기"}
                }
            }
            ,onChange : function(name, value) {
                if (name === "expireDt") {
                    if (value.format("yyyymmdd") < today) {
                        mvno.ui.alert("오늘보다 이전 날짜는 선택할 수 없습니다.", '', function() {
                            PAGE.FORM2.setItemValue("expireDt", defaultExpireDate);
                        });
                    }
                }
            }
            ,onButtonClick : function(btnId) {
                var form = this;
                switch (btnId) {
                    case "btnDetail" :
                        mvno.ui.createForm("FORM3");
                        PAGE.FORM3.setItemValue("text", this.getItemValue("smsText"));
                        mvno.ui.popupWindowById("FORM3", "문자내용", 500, 460, {
                            onClose: function (win) {
                                win.closeForce();
                            }
                        });
                        break;

                    case "btnSend" :
                        if (PAGE.GRID1.getSelectedRowData() === null) {
                            mvno.ui.alert("업무를 선택해주세요.");
                            return false;
                        }

                        if (PAGE.GRID2.getSelectedRowData() === null) {
                            mvno.ui.alert("문자 템플릿을 선택해주세요.");
                            return false;
                        }
                        var formMobileNo = PAGE.FORM2.getItemValue("mobileNo");
                        if (!(formMobileNo && formMobileNo.trim())) {
                            mvno.ui.alert("수신번호를 입력해주세요.");
                            return false;
                        }

                        var finalMobileNo;
                        if (formMobileNo !== maskedMobileNo) {
                            if (!mvno.etc.checkPhoneNumber(formMobileNo)) {
                                mvno.ui.alert("수신번호 형식이 올바르지 않습니다.");
                                return false;
                            }
                            finalMobileNo = formMobileNo;
                        } else {
                            finalMobileNo = "";
                        }

                        var expireDt = PAGE.FORM2.getItemValue("expireDt");
                        if (!expireDt) {
                            mvno.ui.alert("만료일자를 입력해주세요.");
                            return false;
                        }
                        if (expireDt.format("yyyymmdd") < today) {
                            mvno.ui.alert("오늘보다 이전 날짜는 선택할 수 없습니다.");
                            return false;
                        }

                        var data = {
                            workTmplId : PAGE.GRID1.getSelectedRowData().workTmplId,
                            smsTemplateId : PAGE.GRID2.getSelectedRowData().smsTemplateId,
                            mobileNo : finalMobileNo,
                            expireDt : expireDt.format("yyyymmdd"),
                            resNo : resNo,
                            viewType : viewType
                        }

                        mvno.ui.confirm("문자를 전송하시겠습니까?", function() {
                            mvno.cmn.ajax(ROOT + "/org/workMgmt/requestDocRcv.json", data, function(result) {
                                if (result.result.code === "OK") {
                                    mvno.ui.alert("문자 전송이 완료되었습니다.", '', function() {
                                        mvno.ui.closeWindow();
                                    });
                                } else {
                                    mvno.ui.alert("문자 전송에 실패했습니다.");
                                }
                            })
                        })

                        break;

                    case "btnClose" :
                        mvno.ui.closeWindow();
                        break;
                }
            }
        },
        FORM3 : {
            items : [
                {type:"block", list:[
                        {type:"input", name:"text", rows:"24",labelWidth:0, width:432, offsetLeft:0, readonly:true}
                    ]}
            ]
            , buttons : {
                center : {
                    btnClose : {label : "닫기"}
                }
            }
            ,onButtonClick : function(btnId) {
                switch (btnId) {
                    case "btnClose" :
                        mvno.ui.closeWindowById("FORM3");
                        break;
                }
            }
        }
    };

    var PAGE = {
        onOpen : function() {
            mvno.ui.createForm("FORM1");
            mvno.ui.createGrid("GRID1");
            mvno.ui.createGrid("GRID2");
            mvno.ui.createForm("FORM2");

            PAGE.FORM1.setItemValue("searchBaseDate", today);
            PAGE.FORM2.disableItem("btnDetail");
            mvno.ui.disableButton("FORM2", "btnSend");
            mvno.cmn.ajax4lov( ROOT + "/org/workMgmt/getWorkIdList.json", {}, PAGE.FORM1, "workId");
            mvno.cmn.ajax4lov( ROOT + "/org/workMgmt/getRqstrCdList.json", {}, PAGE.FORM1, "rqstrId");
        }
    };
</script>
