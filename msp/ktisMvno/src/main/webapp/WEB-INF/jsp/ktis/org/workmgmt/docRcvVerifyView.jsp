<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : docRcvVerifyView.jsp
   * @Description : 서류 접수 검수 화면
   * @
   * @ 수정일	    수정자 수정내용
   * @ ---------- ------ -----------------------------
   * @ 2025.09.10      최초생성
   * @Create Date : 2025.09.10
   */
%>

<div class="section">
  <div class="section-header">
    <h2>업무정보</h2>
  </div>
</div>
<div id="FORM1" class="section-box"></div>

<div id="GRID1"></div>

<div class="section">
  <div class="section-header">
    <h2>문자템플릿 상세</h2>
  </div>
</div>
<div id="FORM2" class="section-box"></div>

<div id="FORM3"></div>

<div id="POPUP">
    <div id="GRID2" class="section-full"></div>
</div>

<script type="text/javascript">
    var docRcvId = "${docRcvId}";

    var date = new Date();
    var today = date.format("yyyymmdd");
    var defaultExpireDate = new Date(date.setDate(date.getDate() + 3));

    var userId = '${loginInfo.userId}';
    var userName = '${loginInfo.userName}';

    var orgId = '${orgnInfo.orgnId}';
    var orgNm = '${orgnInfo.orgnNm}';
    var orgType = "";

    //조직 유형 - S:판매점, D:대리점, K:개통센터, "10:본사조직, 20:대리점, 30:판매점"
    var typeCd = '${orgnInfo.typeCd}';
    if(typeCd == "10") {
        orgType = "K";
    }else if(typeCd == "20") {
        orgType = "D";
    }else if(typeCd == "30") {
        orgType = "S";
    }

    var modifyFlag = "N";
    var userOrgnTypeCd = '${loginInfo.userOrgnTypeCd}';
    if(userOrgnTypeCd == "10") {
        modifyFlag = "Y";
    }

    var blackMakingYn = "Y";					// 블랙마킹사용권한
    var blackMakingFlag = "N";					// 블랙마킹해제권한 (마스킹권한여부에 따라 변화)
    var maskingYn = '${maskingYn}';				// 마스킹권한
    if(maskingYn == "Y") {
        blackMakingFlag = "Y";
    }

    var agentVersion = '${agentVersion}';		// 스캔버전
    var serverUrl = '${serverUrl}';				// 서버환경 (로컬 : L, 개발 : D, 스테이징 : S, 운영 : R)
    var scanSearchUrl = '${scanSearchUrl}';		// 스캔호출 url
    var scanDownloadUrl = '${scanDownloadUrl}';	// 스캔파일 download url

    var DHX = {
        FORM1 : {
            items : [
                { type : "settings", position : "label-left", labelAlign : "left", labelWidth : 55, blockOffset : 0 }
                ,{ type : "block", list : [
                        {type:"input", width:90, label:"업무유형", name: "workNm", offsetLeft:20, disabled: true}
                        , {type: "newcolumn"}
                        , {type:"input", width:636, label:"업무명", name: "workTmplNm", offsetLeft:31, disabled: true}
                    ]}
                ,{ type : "block", list : [
                        {type:"input", width:90, label:"수신번호", name: "mobileNo", offsetLeft:20, disabled: true}
                        , {type: "newcolumn"}
                        , {type:"input", width:90, label:"예약번호", name: "resNo", offsetLeft:31, disabled: true}
                        , {type: "newcolumn"}
                        , {type:"input", width:90, label:"요칭일자", name: "requestDay", offsetLeft:31, disabled: true}
                        , {type: "newcolumn"}
                        , {type:"input", width:90, label:"만료일자", name: "expireDt", offsetLeft:31, disabled: true}
                        , {type: "newcolumn"}
                        , {type:"input", width:90, label:"URL상태", name: "urlStatusNm", offsetLeft:31, disabled: true}
                    ]}
                ,{ type : "block", list : [
                        {type:"input", width:90, label:"접수상태", name: "rcvYnNm", offsetLeft:20, disabled: true}
                        , {type: "newcolumn"}
                        , {type:"input", width:90, label:"접수일자", name: "rcvDt", offsetLeft:31, disabled: true}
                        , {type: "newcolumn"}
                        , {type:"input", width:90, label:"처리상태", name: "procStatusNm", offsetLeft:31, disabled: true}
                        , {type: "newcolumn"}
                        , {type:"input", width:90, label:"처리일자", name: "procDt", offsetLeft:31, disabled: true}
                        , {type:"hidden", label:"요청번호", name: "docRcvId"}
                        , {type:"hidden", label:"스캔ID", name: "scanId"}
                        , {type:"hidden", label:"URL상태", name: "urlStatus"}
                        , {type:"hidden", label:"접수상태", name: "rcvYn"}
                        , {type:"hidden", label:"처리상태", name: "procStatus"}
                        , {type:"hidden", label:"템플릿ID", name: "smsTemplateId"}
                    ]}
            ]
        },
        GRID1 : {
            css : {
                height : "255px"
            }
            ,title : "수취서류"
            ,header : "서류SEQ,서류코드,수취서류명,파일처리상태,파일개수,접수일시,파일처리상태코드" // 7
            ,columnIds :"itemSeq,itemId,itemNm,fileStatusNm,fileCnt,fileRcvDt,status" // 7
            ,widths :"10,130,440,100,70,140,10" // 7
            ,colAlign :"center,center,left,center,center,center,center" // 7
            ,colTypes :"ron,ro,ro,ro,ron,roXdt,ro" // 7
            ,colSorting :"int,str,str,str,int,str,str" // 7
            ,hiddenColumns : [0, 6]
            ,multiCheckbox : true
            ,buttons : {
                hright: {
                    btnOtp: {label: "인증키(OTP)재발송"}
                    , btnView: {label: "접수서류확인"}
                    , btnComplete: {label: "처리완료"}
                }
                , left: {
                    btnFileLog: {label: "파일이력"}
                }
                , right: {
                    btnVerify: {label: "검수완료"}
                    , btnRetry: {label: "재접수요청"}
                }
            }
            , onButtonClick : function(btnId) {
                var grid = this;
                var rowIds = grid.getCheckedRows(0);
                var docRcvId = PAGE.FORM1.getItemValue("docRcvId");
                var procStatus = PAGE.FORM1.getItemValue("procStatus");
                var procStatusNm = PAGE.FORM1.getItemValue("procStatusNm");
                var smsTemplateId = PAGE.FORM1.getItemValue("smsTemplateId");

                switch(btnId) {
                    case "btnOtp":
                        mvno.ui.confirm("인증키(OTP)를 재발송 하시겠습니까?", function() {
                            mvno.cmn.ajax(ROOT + "/org/workMgmt/resendNewOtp.json", {docRcvId : PAGE.FORM1.getItemValue("docRcvId")}, function(result) {
                                if (result.result.code === "OK") {
                                    mvno.ui.alert("처리가 완료되었습니다.<br>발송 횟수 : " + result.result.data.seq + " / 3");
                                } else {
                                    mvno.ui.alert("처리 중 오류가 발생했습니다.");
                                }
                            });
                        });

                        break;

                    case "btnView":
                        var scanId = PAGE.FORM1.getItemValue("scanId");
                        if (!scanId) {
                            mvno.ui.alert("접수받은 서류가 없습니다.");
                            return false;
                        }

                        var form = this;
                        var data = null;

                        data = "AGENT_VERSION="+agentVersion+
                            "&SERVER_URL="+serverUrl+
                            "&VIEW_TYPE=MCPVIEW"+
                            "&USE_DEL="+modifyFlag+
                            "&USE_STAT="+modifyFlag+
                            "&USE_BM="+blackMakingYn+
                            "&USE_DEL_BM="+blackMakingFlag+
                            "&RGST_PRSN_ID="+userId+
                            "&RGST_PRSN_NM="+userName+
                            "&ORG_TYPE="+orgType+
                            "&ORG_ID="+orgId+
                            "&ORG_NM="+orgNm+
                            // "&RES_NO="+
                            "&PARENT_SCAN_ID="+ scanId
                        ;

                        callViewer(data);
                        break;

                    case "btnComplete":
                        if (procStatus === "N" || procStatus === "R") {
                            mvno.ui.alert("고객이 아직 서류를 등록하지 않았습니다.");
                            return false;
                        }

                        if (procStatus === "C" || procStatus === "E" || procStatus === "I") {
                            mvno.ui.alert("종료된 대상입니다.");
                            return false;
                        }

                        var allRowIdArr = grid.getAllRowIds(",").split(",");
                        var isAllComplete = true;
                        allRowIdArr.forEach(rowId => {
                            var rowData = grid.getRowData(rowId);
                            if (rowData.status !== "C") {
                                isAllComplete = false;
                                return false;
                            }
                        });

                        var confirmMessage = isAllComplete ?
                            "합본 및 처리완료 하시겠습니까?" :
                            "검수완료되지 않은 서류가 있습니다. 검수완료된 서류만 합본 및 처리완료 하시겠습니까?";

                        mvno.ui.confirm(confirmMessage, function() {
                            mvno.cmn.ajax4json(ROOT + "/org/workMgmt/completeDocRcv.json", {"docRcvId" :docRcvId}, function(result) {
                                if (result.result.code === "OK") {
                                    mvno.ui.alert("처리가 완료되었습니다.");
                                    mvno.cmn.ajax(ROOT + "/org/workMgmt/getDocRcvDetail.json", {"docRcvId" :docRcvId}, function(result) {
                                        setData(result.result);
                                    }, {resultTypeCheck : false});
                                } else {
                                    mvno.ui.alert("처리 중 오류가 발생했습니다.");
                                }
                            });
                        });

                        break;

                    case "btnVerify":
                        if (!rowIds) {
                            mvno.ui.alert("ECMN0003");
                            return;
                        }

                        if (procStatus !== "P" && procStatus !== "R") {
                            mvno.ui.alert(procStatusNm + " 상태는 검수(처리)완료가 불가합니다.");
                            return;
                        }

                        var rowIdArr = rowIds.split(",");
                        var isValidVerify = true;
                        rowIdArr.forEach(rowId => {
                            var rowData = grid.getRowData(rowId);
                            if (rowData.status !== "Y") {
                                isValidVerify = false;
                                return false;
                            }
                        });
                        if (!isValidVerify) {
                            mvno.ui.alert("접수된 서류만 검수(처리)완료 가능합니다.");
                            return;
                        }

                        var itemSeqList = new Array();
                        rowIdArr.forEach(rowId => {
                            itemSeqList.push(grid.getRowData(rowId).itemSeq);
                        })

                        var varData = {
                            docRcvId: docRcvId,
                            itemSeqList: itemSeqList
                        }

                        mvno.ui.confirm("총 " + rowIdArr.length + "건을 검수(처리)완료 하시겠습니까?", function() {
                            mvno.cmn.ajax4json(ROOT + "/org/workMgmt/verifyDocRcvItems.json", varData, function(result) {
                                if (result.result.code === "OK") {
                                    mvno.ui.alert("처리가 완료되었습니다.");
                                    mvno.cmn.ajax(ROOT + "/org/workMgmt/getDocRcvDetail.json", {"docRcvId" :docRcvId}, function(result) {
                                        setData(result.result);
                                    }, {resultTypeCheck : false});
                                } else {
                                    mvno.ui.alert("처리 중 오류가 발생했습니다.");
                                }
                            });
                        });

                        break;

                    case "btnRetry":
                        if (!rowIds) {
                            mvno.ui.alert("ECMN0003");
                            return;
                        }

                        if (procStatus !== "P" && procStatus !== "R") {
                            mvno.ui.alert(procStatusNm + " 상태는 재접수요청이 불가합니다.");
                            return;
                        }

                        var rowIdArr = rowIds.split(",");
                        var isValidRetry = true;
                        rowIdArr.forEach(rowId => {
                            var rowData = grid.getRowData(rowId);
                            if (rowData.status === "C") {
                                isValidRetry = false;
                                return false;
                            }
                        });
                        if (!isValidRetry) {
                            mvno.ui.alert("검수완료된 서류는 재접수요청이 불가합니다.");
                            return;
                        }

                        mvno.cmn.ajax(ROOT + "/cmn/smsmgmt/getSendTemplate.json", {"templateId" : smsTemplateId}, function(result) {
                            setDataSms(result.result.template);
                        }, {resultTypeCheck : false});

                        break;

                    case "btnFileLog":
                        var rData = this.getSelectedRowData();
                        if(rData == null) {
                            mvno.ui.alert("선택된 데이터가 존재하지 않습니다.");
                            return;
                        }

                        var varData = {
                            docRcvId: docRcvId,
                            itemId: rData.itemId
                        }

                        mvno.ui.createGrid("GRID2");
                        PAGE.GRID2.list(ROOT + "/org/workMgmt/getDocRcvItemFileListByItemId.json", varData);

                        mvno.ui.popupWindowById("POPUP", "파일이력", 550, 450, {
                            onClose: function(win) {
                                win.closeForce();
                            }
                        });

                        break;
                }
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
                        , {type:"input", label:"수신번호", name:"mobileNo", width:100, offsetLeft:46, disabled:true}
                        , {type:"newcolumn"}
                        , {type:"calendar", label:"만료일자", name:"expireDt", width:100, offsetLeft:46, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", disabled:true, readonly: true}
                    ]}
                , {type:"block", list:[
                        {type:"input", label:"템플릿명", name:"smsTemplateNm", width:730, offsetLeft:10, disabled:true}
                        , {type:"newcolumn"}
                        , {type:"button", value:"문자내용", name:"btnDetail", width:80, offsetLeft:2, disabled:true}
                        , {type:"hidden", value:"문자내용", name:"smsText"}
                    ]}
            ]
            , buttons: {
                center : {
                    btnDocRcv: { label: "문자전송" },
                    btnClose: { label: "닫기" }
                }
            }
            , onButtonClick : function(btnId) {
                var rowIds = PAGE.GRID1.getCheckedRows(0);
                var docRcvId = PAGE.FORM1.getItemValue("docRcvId");
                var procStatus = PAGE.FORM1.getItemValue("procStatus");
                var procStatusNm = PAGE.FORM1.getItemValue("procStatusNm");
                switch (btnId) {
                    case "btnDetail":
                        mvno.ui.createForm("FORM3");
                        PAGE.FORM3.setItemValue("text", this.getItemValue("smsText"));
                        mvno.ui.popupWindowById("FORM3", "문자내용", 500, 460, {
                            onClose: function (win) {
                                win.closeForce();
                            }
                        });
                        break;

                    case "btnDocRcv":
                        if (!rowIds) {
                            mvno.ui.alert("ECMN0003");
                            return;
                        }

                        if (procStatus !== "P" && procStatus !== "R") {
                            mvno.ui.alert(procStatusNm + " 상태는 재접수요청이 불가합니다.");
                            return;
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

                        var rowIdArr = rowIds.split(",");
                        var isValidRetry = true;
                        rowIdArr.forEach(rowId => {
                            var rowData = PAGE.GRID1.getRowData(rowId);
                            if (rowData.status === "C") {
                                isValidRetry = false;
                                return false;
                            }
                        });
                        if (!isValidRetry) {
                            mvno.ui.alert("검수완료된 서류는 재접수요청이 불가합니다.");
                            return;
                        }

                        var itemSeqList = new Array();
                        rowIdArr.forEach(rowId => {
                            itemSeqList.push(PAGE.GRID1.getRowData(rowId).itemSeq);
                        })

                        var varData = {
                            docRcvId: docRcvId,
                            itemSeqList: itemSeqList,
                            expireDt: expireDt.format("yyyymmdd")
                        }

                        mvno.ui.confirm("재접수요청 서류들은 기존 접수받은 서류가 삭제 처리됩니다.<br/>총 " + rowIdArr.length + "건을 재접수요청 하시겠습니까?", function() {
                            mvno.cmn.ajax4json(ROOT + "/org/workMgmt/retryDocRcvItems.json", varData, function (result) {
                                if (result.result.code === "OK") {
                                    mvno.ui.alert("처리가 완료되었습니다.");
                                    mvno.cmn.ajax(ROOT + "/org/workMgmt/getDocRcvDetail.json", {"docRcvId": docRcvId}, function (result) {
                                        setData(result.result);
                                    }, {resultTypeCheck: false});
                                } else {
                                    mvno.ui.alert("처리 중 오류가 발생했습니다.");
                                }
                            });
                        });

                        break;

                    case "btnClose":
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
        },
        GRID2 : {
            css : {
                height : "325px"
            }
            ,header : "서류SEQ,파일ID,등록일시,확장자,결과코드,사유" // 6
            ,columnIds :"itemSeq,fileId,cretDt,ext,resultCode,resultMessage" // 6
            ,widths :"10,10,140,60,90,180" // 6
            ,colAlign :"center,center,center,center,center,center" // 6
            ,colTypes :"ron,ro,roXdt,ro,ro,ro" // 6
            ,colSorting :"int,str,str,str,str,str" // 6
            ,hiddenColumns : [0, 1]
            ,buttons : {
                center : {
                    btnClose : {label : "닫기"}
                }
            }
            ,onButtonClick : function(btnId) {
                switch (btnId) {
                    case "btnClose" :
                        mvno.ui.closeWindowById("GRID2");
                        break;
                }
            }
        }
    };
    var PAGE = {
        onOpen : function() {
            mvno.ui.createForm("FORM1");
            mvno.ui.createGrid("GRID1");
            mvno.ui.createForm("FORM2");

            mvno.cmn.ajax(ROOT + "/org/workMgmt/getDocRcvDetail.json", {"docRcvId" :docRcvId}, function(result) {
                setData(result.result);
            }, {resultTypeCheck : false});
        }
    };

    var setData = function (data) {
        PAGE.FORM1.clear();
        PAGE.GRID1.clearAll();
        PAGE.FORM2.clear();

        PAGE.FORM1.setItemValue("workNm", data.workNm);
        PAGE.FORM1.setItemValue("workTmplNm", data.workTmplNm);
        PAGE.FORM1.setItemValue("mobileNo", data.mobileNo);
        PAGE.FORM1.setItemValue("resNo", data.resNo);
        PAGE.FORM1.setItemValue("requestDay", getFormattedDay(data.requestDay));
        PAGE.FORM1.setItemValue("expireDt", getFormattedDay(data.docRcvUrlVO.expireDt, -1));
        PAGE.FORM1.setItemValue("urlStatusNm", data.docRcvUrlVO.urlStatusNm);
        PAGE.FORM1.setItemValue("rcvYnNm", data.docRcvStatusVO.rcvYnNm);
        PAGE.FORM1.setItemValue("rcvDt", getFormattedDay(data.docRcvStatusVO.rcvDt));
        PAGE.FORM1.setItemValue("procStatusNm", data.docRcvStatusVO.procStatusNm);
        PAGE.FORM1.setItemValue("procDt", getFormattedDay(data.docRcvStatusVO.procDt));
        PAGE.FORM1.setItemValue("docRcvId", data.docRcvId);
        PAGE.FORM1.setItemValue("scanId", data.scanId);
        PAGE.FORM1.setItemValue("urlStatus", data.docRcvUrlVO.status);
        PAGE.FORM1.setItemValue("rcvYn", data.docRcvStatusVO.rcvYn);
        PAGE.FORM1.setItemValue("procStatus", data.docRcvStatusVO.procStatus);
        PAGE.FORM1.setItemValue("smsTemplateId", data.smsTemplateId);

        if (data.itemList && data.itemList.length > 0) {
            data.itemList.forEach(item => {
                PAGE.GRID1.appendRow(item);
            });
        }

        if (data.docRcvStatusVO.procStatus === "P" || data.docRcvStatusVO.procStatus === "R") {
            mvno.ui.enableButton("GRID1", "btnVerify");
            mvno.ui.enableButton("GRID1", "btnRetry");
        } else {
            mvno.ui.disableButton("GRID1", "btnVerify");
            mvno.ui.disableButton("GRID1", "btnRetry");
        }

        if (data.docRcvStatusVO.procStatus === "N" || data.docRcvStatusVO.procStatus === "R") {
            mvno.ui.enableButton("GRID1", "btnOtp");
        } else {
            mvno.ui.disableButton("GRID1", "btnOtp");
        }

        if (data.docRcvStatusVO.procStatus === "P" || data.docRcvStatusVO.procStatus === "V") {
            mvno.ui.enableButton("GRID1", "btnComplete");
        } else {
            mvno.ui.disableButton("GRID1", "btnComplete");
        }

        if (data.scanId && data.docRcvStatusVO.procStatus !== "M" && data.docRcvStatusVO.procStatus !== "F") {
            mvno.ui.enableButton("GRID1", "btnView");
        } else {
            mvno.ui.disableButton("GRID1", "btnView");
        }

        PAGE.FORM2.disableItem("expireDt");
        PAGE.FORM2.disableItem("btnDetail");
        mvno.ui.disableButton("FORM2", "btnDocRcv");
    }

    var setDataSms = function (data) {
        PAGE.FORM2.clear();

        PAGE.FORM2.setItemValue("smsTemplateId", data.templateId);
        PAGE.FORM2.setItemValue("callback", data.callbackNum);
        PAGE.FORM2.setItemValue("mobileNo", PAGE.FORM1.getItemValue("mobileNo"));
        PAGE.FORM2.setItemValue("expireDt", defaultExpireDate);
        PAGE.FORM2.setItemValue("smsTemplateNm", data.templateNm);
        PAGE.FORM2.setItemValue("smsText", data.text);

        mvno.ui.enableButton("FORM2", "btnDocRcv");
        PAGE.FORM2.enableItem("expireDt");
        PAGE.FORM2.enableItem("btnDetail");
    }

    var getFormattedDay = function (dateParam, dayAdd, delimiter = '-') {
        if (!dateParam || dateParam.length < 8) {
            return dateParam;
        }

        var dateString;
        if (dayAdd) {
            var date = new Date(Number(dateParam.substring(0, 4)), Number(dateParam.substring(4, 6)) - 1, Number(dateParam.substring(6, 8)));
            date.setDate(date.getDate() + dayAdd);
            dateString = date.getFullYear().toString() + (date.getMonth() + 1).toString().padStart(2, "0") + date.getDate().toString().padStart(2, "0");
        } else {
            dateString = dateParam;
        }

        return dateString.substring(0, 4) + delimiter + dateString.substring(4, 6) + delimiter + dateString.substring(6, 8);
    }

    //스캔 호출
    function callViewer(data){
        var url = scanSearchUrl + "?" + encodeURIComponent(data);

        // 타임아웃 설정 - 10초이내 응답없으면 서비스 실행상태가 아님.
        var timeOutTimer = window.setTimeout(function (){
            mvno.ui.confirm("이미지 프로그램이 설치되지 않았거나 실행중이 아닙니다. 설치페이지로 이동 하시겠습니까?",function(){
            	window.open(scanDownloadUrl, "", "width=750,height=650,resizable=no,scrollbars=yes,top=10,left=10");		// 설치페이지로 이동
            });
        }, 10000);

        $.ajax({
            type : "GET",
            url : url,
            crossDomain : true,
            dataType : 'jsonp',
            success : function(args){
                window.clearTimeout(timeOutTimer);
                if(args.RESULT == "SUCCESS") {
                    console.log("SUCCESS");
                } else if(args.RESULT == "ERROR_TYPE2") {
                    mvno.ui.alert("버전이 다릅니다. 설치페이지로 이동해주세요.");
                    window.open(scanDownloadUrl, "", "width=750,height=650,resizable=no,scrollbars=yes,top=10,left=10");		// 설치페이지로 이동
                } else {
                    mvno.ui.alert(args.RESULT + " : " + args.RESULT_MSG);
                }
            }
        });
    }
</script>
<!-- Script -->
