<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    /**
     * @Class Name : rateAcenMgmt.jsp
     * @Description : A'CEN 요금제관리
     * @
     * @Create Date : 2024. 06. 27.
     */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="GRID2"></div>

<div id="POPUP1">
    <div id="POPUP1TOP"  class="section-full"></div>
    <div id="POPUP1MID" class="section-box"></div>
</div>

<div id="FORM2" class="section-box"></div>
<div id="FORM3" class="section-search"></div>




<!-- Script -->
<script type="text/javascript">
    var allChkYN = true;
    var today = new Date().format("yyyymmdd");
    var now = new Date().format("yyyymmddhhmiss");

    var DHX = {
        //------------------------------------------------------------
        FORM1 : {
            items : [
                {type:"settings", position:"label-left", labelAlign:"left",  blockOffset:0},
                {type: "block", list: [
                        {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'stdrDt', label: '기준일자', calendarPosition: 'bottom', width:100, required:true},
                        {type: 'newcolumn'},
                        {type: "select", name: "searchGb", offsetLeft:20, width:100, options:[{value:"rateNm", text:"요금제명"}, {value:"rateCd", text:"요금제코드"}]},
                        {type: "newcolumn"},
                        {type: "input", name: "searchVal", width:120, maxLength:40}
                ]},
                {type: "newcolumn"}
                ,{type: "button", value: "조회", name: "btnSearch" , className:"btn-search1"}
                ,{ type : 'hidden', name: "btnCount1", value:"0"}
            ],

            onButtonClick : function(btnId) {

                var form = this;

                switch (btnId) {

                    case "btnSearch":
                        form.setItemValue("searchVal", form.getItemValue("searchVal").trim());
                        PAGE.GRID1.list(ROOT + "/sale/rateMgmt/getRateAcenMgmtList.json", form);
                        break;
                }
            }
        },
        //-------------------------------------------------------------------------------------
        //요금제리스트
        GRID1 : {
            css : {
                height : "320px"
            },
            title : "요금제목록",
            header : "선택,요금제코드,요금제유형,요금제명,할인후월정액(VAT포함),적용시작일시,시작시간,적용종료일시,종료시간,등록자,등록일시,수정자,수정일시,최신여부",
            columnIds : "rowCheck,rateCd,rateTypeNm,rateNm,prmtVatAmt,pstngStartDate,startTm,pstngEndDate,endTm,cretNm,cretDt,amdNm,amdDt,newestYn",
            colAlign : "center,left,center,left,right,center,center,center,center,center,center,center,center,center",
            colTypes : "ch,ro,ro,ro,ron,roXdt,ro,roXdt,ro,ro,roXdt,ro,roXdt,ro",
            colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str",
            widths : "50,90,80,220,140,120,80,120,80,80,120,80,120,80",
            paging: true,
            pageSize:10,
            hiddenColumns : "6,8,13",
            buttons : {
                hright : {
                    btnUpload : { label : "엑셀업로드" }
                    ,btnDnExcel : { label : "엑셀다운로드" }
                }
                ,left : {
                    btnAllChk : {label : "전체선택" }
                    ,btnEndDt : { label : "종료일시변경" }
                    ,btnTemplate : { label : "엑셀등록양식" }
                }
                ,right : {
                    btnReg : { label : "등록" },
                    btnMod : { label : "수정" }
                }
            },
            checkSelectedButtons : ["btnMod"],
            onRowDblClicked : function(rowId, celInd) {
                // 수정버튼 누른것과 같이 처리
                this.callEvent("onButtonClick", ['btnMod']);
            },
            onRowSelect:function(rowId){
                var grid = this;
                var data = grid.getSelectedRowData();
                PAGE.GRID2.list(ROOT + "/sale/rateMgmt/getRateAcenHist.json", data)

                var cell = this.cells(rowId,0);
                if(!cell.isChecked()) cell.setValue(1);
                else cell.setValue(0);
            },
            onButtonClick : function(btnId, selectedData) {
                var form = this;
                var grid = this;
                var chkData = grid.getCheckedRowData();

                switch (btnId) {

                    case "btnAllChk" :

                        this.forEachRow(function(id){

                            var cell = this.cells(id,0);

                            if (cell.isCheckbox()){
                                if(allChkYN){
                                    if(!cell.isChecked()) cell.setValue(1);

                                }else{
                                    if(cell.isChecked()) cell.setValue(0);
                                }
                            }
                        });

                        allChkYN = !allChkYN;

                        break;

                    case "btnEndDt" :

                        if(chkData.length < 1 ){
                            mvno.ui.alert("ECMN0003");
                            return;
                        }

                        mvno.ui.createForm("FORM3");

                        PAGE.FORM3.setItemValue("pstngEndDateMod", today);
                        PAGE.FORM3.setItemValue("endTmMod", "235959");

                        mvno.ui.popupWindowById("FORM3", "적용종료일시변경", 450, 150, {
                            onClose: function(win) {
                                if (!PAGE.FORM3.isChanged()) {
                                    return true;
                                }
                                mvno.ui.confirm("CCMN0005", function() {
                                    win.closeForce();
                                });
                            }
                        });

                        break;

                    case "btnTemplate" :

                        mvno.cmn.download("/sale/rateMgmt/getRateAcenExcelTemp.json");

                        break;

                    case "btnUpload" :

                        mvno.ui.createGrid("POPUP1TOP");
                        mvno.ui.createForm("POPUP1MID");

                        PAGE.POPUP1TOP.clearAll();
                        PAGE.POPUP1MID.clear();
                        PAGE.POPUP1MID.setFormData({});

                        PAGE.POPUP1MID.attachEvent("onFileRemove",function(realName, serverName){
                            PAGE.POPUP1TOP.clearAll();

                            return true;
                        });

                        var uploader = PAGE.POPUP1MID.getUploader("file_upload1");
                        uploader.revive();

                        mvno.ui.popupWindowById("POPUP1", "A'Cen 요금제엑셀등록", 950, 720, {
                            onClose2: function(win) {
                                uploader.reset();
                            }
                        });

                        break;

                    case "btnReg":
                        mvno.ui.createForm("FORM2");

                        PAGE.FORM2.setFormData(true);
                        PAGE.FORM2.setItemValue("flag", "I");

                        PAGE.FORM2.setItemValue("pstngStartDate", today);
                        PAGE.FORM2.setItemValue("startTm", "000000");
                        PAGE.FORM2.setItemValue("pstngEndDate", "99991231");
                        PAGE.FORM2.setItemValue("endTm", "235959")

                        PAGE.FORM2.enableItem("rateCd");
                        PAGE.FORM2.enableItem("pstngStartDate");
                        PAGE.FORM2.enableItem("startTm");

                        PAGE.FORM2.hideItem("pstngEndDateMod");
                        PAGE.FORM2.hideItem("endTmMod");
                        PAGE.FORM2.showItem("pstngEndDate");
                        PAGE.FORM2.showItem("endTm");

                        PAGE.FORM2.disableItem("pstngEndDate");
                        PAGE.FORM2.disableItem("endTm");

                        PAGE.FORM2.clearChanged();

                        mvno.ui.popupWindowById("FORM2", "요금제등록", 480, 720, {
                            onClose: function(win) {
                                if (! PAGE.FORM2.isChanged()) return true;
                                mvno.ui.confirm("CCMN0005", function(){
                                    win.closeForce();
                                });
                            }
                        });
                        break;

                    case "btnMod":

                        mvno.ui.createForm("FORM2");
                        mvno.ui.hideButton("FORM2" , "btnInit");

                        PAGE.FORM2.clear();
                        PAGE.FORM2.setFormData(selectedData);
                        PAGE.FORM2.setItemValue("flag", "U");

                        PAGE.FORM2.setItemValue("pstngEndDateMod", PAGE.FORM2.getItemValue("pstngEndDate"));
                        PAGE.FORM2.setItemValue("endTmMod", PAGE.FORM2.getItemValue("endTm"));

                        PAGE.FORM2.hideItem("pstngEndDate");
                        PAGE.FORM2.hideItem("endTm");
                        PAGE.FORM2.showItem("pstngEndDateMod");
                        PAGE.FORM2.showItem("endTmMod");

                        PAGE.FORM2.disableItem("rateCd");
                        PAGE.FORM2.disableItem("pstngStartDate");
                        PAGE.FORM2.disableItem("startTm");

                        PAGE.FORM2.enableItem("pstngEndDateMod");
                        PAGE.FORM2.enableItem("endTmMod");

                        PAGE.FORM2.clearChanged();

                        mvno.ui.popupWindowById("FORM2", "요금제수정", 480, 720, {
                            onClose: function(win) {
                                if (! PAGE.FORM2.isChanged()) return true;
                                mvno.ui.confirm("CCMN0005", function(){
                                    win.closeForce();
                                });
                            }
                        });

                        break;

                    case "btnDnExcel":

                        if(PAGE.GRID1.getRowsNum() == 0) {
                            mvno.ui.alert("데이터가 존재하지 않습니다.");
                            return;
                        }

                        var searchData =  PAGE.FORM1.getFormData(true);
                        mvno.cmn.download(ROOT + "/sale/rateMgmt/getRateAcenMgmtListExcel.json?menuId=MSP2001015",true,{postData:searchData});

                        break;
                }
            }
        },
        // --------------------------------------------------------------------------
        // 엑셀 등록 화면
        POPUP1TOP : {
            css : { height : "465px" }
            ,title : "A'Cen 엑셀등록"
            ,header : "요금제코드,요금제명,월정액(VAT포함),할인후월정액(VAT포함),음성(기본/분),음성(추가/분),기타통화(분),문자(기본/건),문자(추가/건),데이터(기본/MB),데이터(추가/MB),데이터(일/MB),QOS,QOS(단위),적용시작일,적용시작시간,적용종료일,적용종료시간"
            ,columnIds : "rateCd,rateNm,baseVatAmt,prmtVatAmt,callCnt,prmtCallCnt,etcCallCnt,smsCnt,prmtSmsCnt,dataCnt,prmtDataCnt,dayDataCnt,qosDataCntDesc,qosDataUnit,pstngStartDate,startTm,pstngEndDate,endTm"
            ,widths : "90,220,100,140,120,120,120,120,120,120,120,120,120,120,120,120,120,120,120"
            ,colAlign : "left,left,right,right,right,right,right,right,right,right,right,right,right,right,right,center,center,center,center"
            ,colTypes : "ro,ro,ron,ron,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
        }   //POPUP1TOP End
        , POPUP1MID : {
            title : "엑셀업로드"
            ,items : [
                { type : "block"
                    ,list : [
                        { type : "newcolumn", offset : 20 }
                        ,{ type : "upload"
                            ,label : "A'Cen 엑셀등록파일"
                            ,name : "file_upload1"
                            ,inputWidth : 830
                            ,url : "/rcp/dlvyMgmt/regParExcelUp.do"
                            ,userdata : { limitSize:10000 }
                            ,autoStart : true }
                    ]},
                {type:"input", width:100, name : "rowData", hidden:true}
            ]

            ,buttons : {
                center : {
                    btnSave : {label : "저장" },
                    btnClose : { label : "닫기" }
                }
            }
            ,onUploadFile : function(realName, serverName) {
                fileName = serverName;
            }
            ,onUploadComplete : function(count){

                var url = ROOT + "/sale/rateMgmt/readRateAcenExcelUpList.json";
                var userOptions = {timeout:180000};

                mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
                    var rData = resultData.result;

                    PAGE.POPUP1TOP.clearAll();

                    PAGE.POPUP1TOP.parse(rData.data.rows, "js");

                    PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
                }, userOptions);
            }
            ,onButtonClick : function(btnId) {

                var form = this; // 혼란방지용으로 미리 설정 (권고)

                switch (btnId) {

                    case "btnSave" :

                        if(PAGE.POPUP1TOP.getRowsNum() == 0) {
                            mvno.ui.alert("파일을 첨부해 주세요.");
                            break;
                        }

                        var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
                        PAGE.FORM1.setItemValue("btnCount1", btnCount2)

                        if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") {
                            return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기
                        }

                        var arrObj = [];

                        PAGE.POPUP1TOP.forEachRow(function(id){
                            var arrData = PAGE.POPUP1TOP.getRowData(id);
                            arrObj.push(arrData);
                        });

                        var sData = { items : arrObj };

                        var userOptions = {timeout:180000
                            , errorCallback : function(result){
                                PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
                            }
                        };

                        mvno.cmn.ajax4json(ROOT + "/sale/rateMgmt/regRateAcenExcel.json", sData, function(result) {
                            mvno.ui.alert(result.result.msg);
                            PAGE.GRID1.refresh();
                            mvno.ui.closeWindowById("POPUP1");
                        }, userOptions);

                        break;

                    case "btnClose":
                        mvno.ui.closeWindowById("POPUP1");

                        break;
                }
            }
        }
        ,GRID2 : {
            css: {
                height: "160px"
            },
            title: "변동이력",
            header: "요금제코드,요금제유형,요금제명,할인후월정액(VAT포함),적용시작일시,시작시간,적용종료일시,종료시간,등록자,등록일시,수정자,수정일시",
            columnIds: "rateCd,rateTypeNm,rateNm,prmtVatAmt,pstngStartDate,startTm,pstngEndDate,endTm,cretNm,cretDt,amdNm,amdDt",
            colAlign: "left,center,left,right,center,center,center,center,center,center,center,center",
            colTypes: "ro,ro,ro,ron,roXdt,ro,roXdt,ro,ro,roXdt,ro,roXdt",
            colSorting: "str,str,str,str,str,str,str,str,str,str,str,str",
            widths: "90,80,220,140,120,80,120,80,80,120,80,120",
            paging: true,
            pageSize:10,
            hiddenColumns: "5,7",
            buttons: {
                hright: {
                    btnDnExcel: {label: "엑셀다운로드"}
                }
            }
            , onButtonClick: function (btnId) {

                var form = this; // 혼란방지용으로 미리 설정 (권고)

                switch (btnId) {

                    case "btnDnExcel":

                        if(PAGE.GRID2.getRowsNum() == 0) {
                            mvno.ui.alert("데이터가 존재하지 않습니다.");
                            return;
                        }

                        var searchData =  PAGE.GRID1.getSelectedRowData();
                        mvno.cmn.download(ROOT + "/sale/rateMgmt/getRateAcenHistExcel.json?menuId=MSP2001015",true,{postData:searchData});

                        break;
                }
            }
        }
        // --------------------------------------------------------------------------
        //요금제 등록/수정 화면
        ,FORM2 :{
            title : "",
            items : [
                {type: "settings", position: "label-left", labelWidth: "150",  inputWidth: "auto"},

                {type: "fieldset", label: "기본정보", inputWidth:380, offsetLeft: 15, list:[
                        ,{type:"block", list:[
                                {type: "input", name: "rateCd", label: "요금제코드", width:166, validate:"ValidAplhaNumeric,mvno.vxRule.excludeKorean", required: true, maxLength: 10}
                            ]}
                        ,{type:"block", list:[
                                {type: "input", name: "rateNm", label: "요금제명", width:166, validate: "NotEmpty", required: true, maxLength: 30}
                            ]}
                    ]},

                {type: "fieldset", label: "제공량", inputWidth:380, offsetLeft: 15, list:[
                        ,{type:"block", list:[
                                {type: "input", name: "callCnt", label: "음성(기본/분)",   required: true, maxLength: 10, width:166,  userdata:{align:"right"}}
                            ]}
                        ,{type:"block", list:[
                                {type: "input", name: "prmtCallCnt", label: "음성(추가/분)",   required: true, maxLength: 10, width:166, validate:"ValidInteger"}
                            ]}
                        ,{type:"block", list:[
                                {type: "input", name: "etcCallCnt", label: "기타통화(분)",   required: true, maxLength: 10, width:166, validate:"ValidInteger"}
                            ]}
                        ,{type:"block", list:[
                                {type: "input", name: "smsCnt", label: "문자(기본/건)",   required: true, maxLength: 10, width:166,  userdata:{align:"right"}}
                            ]}
                        ,{type:"block", list:[
                                {type: "input", name: "prmtSmsCnt", label: "문자(추가/건)",   required: true, maxLength: 10, width:166, validate:"ValidInteger"}
                            ]}
                        ,{type:"block", list:[
                                {type: "input", name: "dataCnt", label: "데이터(기본/MB)",   required: true, maxLength: 10, width:166, validate:"ValidInteger"}
                            ]}
                        ,{type:"block", list:[
                                {type: "input", name: "prmtDataCnt", label: "데이터(추가/MB)",   required: true, maxLength: 10, width:166, validate:"ValidInteger"}
                            ]}
                        ,{type:"block", list:[
                                {type: "input", name: "dayDataCnt", label: "데이터(일/MB)",   required: true, maxLength: 10, width:166,  validate:"ValidInteger"}
                            ]}
                        ,{type:"block", list:[
                                {type: "input", name: "qosDataCntDesc", label: "QOS",   required: true, maxLength: 10, width:166, validate:"ValidInteger" }
                            ]}
                        ,{type:"block", list:[
                                {type: "input", name: "qosDataUnit", label: "QOS(단위)",   required: true,width:166, maxLength: 10, userdata:{align:"right"}}
                            ]}
                    ]},

                {type: "fieldset", label: "할인금액", inputWidth:380, offsetLeft: 15, list:[
                        ,{type:"block", list:[
                                {type: "input", name: "baseVatAmt", label: "월정액(VAT포함)",   required: true, maxLength: 10, validate:"ValidInteger", width:166, numberFormat:"0,000,000,000"}
                            ]}
                        ,{type:"block", list:[
                                {type: "input", name: "disVatAmt", label: "할인금액(VAT포함)",   readonly: true, maxLength: 10, validate:"ValidInteger", width:166, numberFormat:"0,000,000,000"}
                            ]}
                        ,{type:"block", list:[
                                {type: "input", name: "prmtVatAmt", label: "할인후월정액(VAT포함)",   required: true, maxLength: 10, validate:"ValidInteger", width:166, numberFormat:"0,000,000,000"}
                            ]}
                        ,{type:"block", list:[
                                {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'pstngStartDate', label: '적용시작일시', calendarPosition: 'bottom', width:100, required:true, readonly: true},
                                {type: "newcolumn"},
                                {type: "input",   name: "startTm",  maxLength:6, width: 60, validate:"ValidInteger"}
                            ]}
                        ,{type:"block", list:[
                                {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'pstngEndDate', label: '적용종료일시', calendarPosition: 'bottom', width:100, readonly: true},
                                {type: "newcolumn"},
                                {type: "input",   name: "endTm",  maxLength:6, width: 60, validate:"ValidInteger"},
                                {type: "newcolumn"},
                                {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'pstngEndDateMod', label: '적용종료일시', calendarPosition: 'bottom' ,width:100, hidden:true, readonly: true},
                                {type: "newcolumn"},
                                {type: "input", name: "endTmMod", width: 60, validate:"ValidInteger", maxLength:6, hidden:true}
                            ]}
                    ]},
                {type: "hidden", name: "flag"}
                ,{type: "hidden", name: "newestYn"}
            ],
            buttons : {
                 left : {
                    btnInit : { label : "초기화" }
                },
                center : {
                    btnSave : { label : "저장" },
                    btnClose : { label: "닫기" }
                }
            }
            ,onInputChange : function(name,val,inp) {
                switch(name)
                {
                    case "baseVatAmt":

                        var prmtVatAmt = PAGE.FORM2.getItemValue("prmtVatAmt").replace(/[^0-9]/g,"");
                        this.setItemValue("disVatAmt", val.replace(/[^0-9]/g,"") - prmtVatAmt);
                        break;

                    case "prmtVatAmt":

                        var baseVatAmt = PAGE.FORM2.getItemValue("baseVatAmt").replace(/[^0-9]/g,"");
                        this.setItemValue("disVatAmt", baseVatAmt - val.replace(/[^0-9]/g,""));
                        break;
                }
            }
            ,onKeyUp : function (inp, ev, name, value){
                if(name == "baseVatAmt" || name == "prmtVatAmt" ||  name == "prmtCallCnt" || name == "etcCallCnt"  || name == "prmtSmsCnt" || name == "dataCnt" || name == "prmtDataCnt" || name == "dayDataCnt" || name == "qosDataCntDesc" || name == "startTm" || name == "endTm"){
                    var keyId = ev.keyCode;
                    if(!((keyId >= 48 && keyId <=57) ||( keyId >= 96 && keyId <= 105 ) || keyId == 8 || keyId == 46 || keyId == 37 || keyId == 39)){
                        var val = PAGE.FORM2.getItemValue(name);
                        PAGE.FORM2.setItemValue(name,val.replace(/[^0-9]/g,""));


                    }
                }
            }
            ,onButtonClick : function(btnId) {

                var form = this; // 혼란방지용으로 미리 설정 (권고)

                switch (btnId) {
                    case "btnInit":
                        if(form.getItemValue("flag") == "U") return;

                        PAGE.FORM2.clear();

                        PAGE.FORM2.setFormData(true);
                        PAGE.FORM2.setItemValue("flag", "I");
                        PAGE.FORM2.setItemValue("pstngStartDate", today);
                        PAGE.FORM2.setItemValue("startTm", "000000");
                        PAGE.FORM2.setItemValue("pstngEndDate", "99991231");
                        PAGE.FORM2.setItemValue("endTm", "235959")

                        break;

                    case "btnSave":

                        var url;
                        var msg;

                        var formData = PAGE.FORM2.getFormData();

                        if(mvno.cmn.isEmpty(formData.rateCd)){
                            mvno.ui.alert("요금제코드를 입력하세요.");
                            return;
                        }
                        if(mvno.cmn.isEmpty(formData.rateNm)){
                            mvno.ui.alert("요금제명을 입력하세요.");
                            return;
                        }

                        if(mvno.cmn.isEmpty(formData.callCnt)){
                            mvno.ui.alert("음성(기본/분)을 입력하세요.");
                            return;
                        }
                        if(mvno.cmn.isEmpty(formData.prmtCallCnt)){
                            mvno.ui.alert("음성(추가/분)을 입력하세요.");
                            return;
                        }
                        if(mvno.cmn.isEmpty(formData.etcCallCnt)){
                            mvno.ui.alert("기타통화(분)를 입력하세요.");
                            return;
                        }

                        if(mvno.cmn.isEmpty(formData.smsCnt)){
                            mvno.ui.alert("문자(기본/건)를 입력하세요.");
                            return;
                        }
                        if(mvno.cmn.isEmpty(formData.prmtSmsCnt)){
                            mvno.ui.alert("문자(추가/건)를 입력하세요.");
                            return;
                        }

                        if(mvno.cmn.isEmpty(formData.dataCnt)){
                            mvno.ui.alert("데이터(기본/MB)를 입력하세요.");
                            return;
                        }
                        if(mvno.cmn.isEmpty(formData.prmtDataCnt)){
                            mvno.ui.alert("데이터(추가/MB)를 입력하세요.");
                            return;
                        }
                        if(mvno.cmn.isEmpty(formData.dayDataCnt)){
                            mvno.ui.alert("데이터(일/MB)를 입력하세요.");
                            return;
                        }

                        if(mvno.cmn.isEmpty(formData.qosDataCntDesc)){
                            mvno.ui.alert("QOS를 입력하세요.");
                            return;
                        }

                        if(mvno.cmn.isEmpty(formData.qosDataUnit)){
                            mvno.ui.alert("QOS(단위)를 입력하세요.");
                            return;
                        }

                        if(mvno.cmn.isEmpty(formData.qosDataUnit)){
                            mvno.ui.alert("QOS(단위)를 입력하세요.");
                            return;
                        }
                        if(mvno.cmn.isEmpty(formData.baseVatAmt)){
                            mvno.ui.alert("월정액(VAT포함)을 입력하세요.");
                            return;
                        }
                        if(mvno.cmn.isEmpty(formData.qosDataUnit)){
                            mvno.ui.alert("할인후월정액(VAT포함)을 입력하세요.");
                            return;
                        }
                        PAGE.FORM2.setItemValue("baseVatAmt",PAGE.FORM2.getItemValue("baseVatAmt").replace(/[^0-9]/g,""));
                        PAGE.FORM2.setItemValue("prmtVatAmt",PAGE.FORM2.getItemValue("prmtVatAmt").replace(/[^0-9]/g,""));

                        if(formData.startTm.length < 6 || formData.endTm.length < 6 ){
                            mvno.ui.alert("시작/종료 시간은 6자리로 입력해주세요.");
                            return;
                        }

                        if(mvno.cmn.isEmpty(formData.pstngStartDate) || mvno.cmn.isEmpty(formData.startTm)){
                            mvno.ui.alert("적용시작일시를 입력하세요.");
                            return;
                        }
                        if(mvno.cmn.isEmpty(formData.pstngEndDate) || mvno.cmn.isEmpty(formData.endTm)){
                            mvno.ui.alert("적용종료일시를 입력하세요.");
                            return;
                        }

                        if(Number(formData.startTm) <0 || Number(formData.startTm) > 235959) {
                            mvno.ui.alert("정확한 시작시간(시분초) 을 입력하세요");
                            return;
                        }
                        if(Number(formData.endTm) <0 || Number(formData.endTm) > 235959) {
                            mvno.ui.alert("정확한 종료시간(시분초) 을 입력하세요");
                            return;
                        }



                        url = "/sale/rateMgmt/mergeRateAcenCd.json";

                        var pstngStartDate = formData.pstngStartDate.format("yyyymmdd")+formData.startTm;
                        var pstngEndDate = formData.pstngEndDate.format("yyyymmdd")+formData.endTm;

                        if(formData.flag == "I"){

                            if(pstngStartDate > pstngEndDate) {
                                mvno.ui.alert("적용종료일시[" +pstngEndDate+ "]<br>적용시작일시["  +pstngStartDate+ "]<br> 이전으로 등록할수 없습니다.");
                                return;
                            }
                            if(pstngStartDate < now) {
                                mvno.ui.alert("적용시작일시[" +pstngStartDate+ "]<br>  현재["  +now+ "]<br>  이전으로 등록할 수 없습니다.");
                                return;
                            }
                            msg = "NCMN0001";
                        }else{

                            if( formData.endTmMod.length < 6){
                                mvno.ui.alert("종료 시간은 6자리로 입력해주세요.");
                                return;
                            }

                            var pstngEndDateMod = formData.pstngEndDateMod.format("yyyymmdd")+formData.endTmMod;
                            var newestYn = formData.newestYn;

                            //종료된 요금제인 경우
                            if(pstngEndDate < now) {
                                mvno.ui.alert("이 요금제코드는 이미 종료된 요금제코드로 종료일을 변경할 수 없습니다.");
                                return;
                            }
                            //변경할 적용종료일자가 시작일시보다 이전인 경우
                            if(pstngStartDate > pstngEndDateMod) {
                                mvno.ui.alert("변경적용종료일시[" +pstngEndDateMod+ "]<br> 적용시작일시["  +pstngStartDate+ "]<br> 이전으로 변경할수 없습니다.");
                                return;
                            }
                            //변경할 적용종료일자가 현재보다 이전인 경우
                            if(pstngEndDateMod < now) {
                                mvno.ui.alert("변경적용종료일시[" +pstngEndDateMod+ "]<br> 현재 ["  +now+ "] 전입니다.<br> 적용종료일시를 변경하세요.");
                                return;
                            }
                            //** 최신으로 등록된 요금제코드가 아닌경우
                            if(newestYn != "Y" ){
                                //기존 적용종료일자보다 변경할 적용종료일자가 이후인 경우
                                if(pstngEndDateMod > pstngEndDate) {
                                    mvno.ui.alert("변경적용종료일시[" +pstngEndDateMod+ "]<br> 기존적용종료일시[" +pstngEndDate+ "]<br>이후로 변경할 수 없습니다.");
                                    return;
                                }
                            }

                            msg = "NCMN0002";
                        }
                        mvno.cmn.ajax(ROOT + url, form, function(result) {
                            PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
                            mvno.ui.closeWindowById(form, true);
                            mvno.ui.notify(msg);
                        }, {aysnc:false});

                        break;

                    case "btnClose" :
                        mvno.ui.closeWindowById(form);
                        break;

                }
            }
        }
        ,FORM3 : {
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
                {type:"block", list:[
                        ,{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'pstngEndDateMod', label: '종료일자', calendarPosition: 'bottom', width:100}
                        ,{type:"newcolumn", offset:10}
                        ,{type:"input", name: "endTmMod", width: 50, validate:"ValidInteger", maxLength:6}
                    ]},
                , {type:"newcolumn", offset:20}
                , {type :"button",name : "btnEndDtMod",value : "종료일시변경" }
            ]
            ,onButtonClick : function(btnId) {
                var chkData = PAGE.GRID1.getCheckedRowData();

                switch (btnId) {
                    case "btnEndDtMod":

                        // 입력 날짜 유효성 검사
                        var pstngEndDateMod = PAGE.FORM3.getItemValue("pstngEndDateMod");
                        var endTmMod = PAGE.FORM3.getItemValue("endTmMod");

                        if(endTmMod.length < 6){
                            mvno.ui.alert("변경종료 시간은 6자리로 입력해주세요.");
                            return;
                        }

                        if(pstngEndDateMod == null || pstngEndDateMod== ''){
                            mvno.ui.alert("종료일자를 입력해주세요");
                            return;
                        }

                        if(endTmMod == null || endTmMod== '' || endTmMod.length < 1){
                            mvno.ui.alert("종료시간(시분초)을 입력해주세요");
                            return;
                        }
                        if(Number(endTmMod) < 0 || Number(endTmMod) > 235959) {
                            mvno.ui.alert("정확한 종료시간(시분초)을 입력해주세요");
                            return;
                        }

                        var pstngEndDateMod = pstngEndDateMod.format("yyyymmdd")+endTmMod;

                        var jsonObj = {};
                        var arrObj = [];

                        for(var i = 0; i < chkData.length; i++){
                            var rateCd = chkData[i].rateCd;
                            var pstngStartDate = chkData[i].pstngStartDate;
                            var pstngEndDate = chkData[i].pstngEndDate;
                            var newestYn = chkData[i].newestYn;
                            //종료된 요금제인 경우
                            if(pstngEndDate < now){
                                mvno.ui.alert( "요금제코드["+ rateCd +"]은<br> 이미 종료된 요금제코드로 종료일을 변경할수 없습니다.");
                                return;
                            }
                            //변경할 적용종료일자가 시작일시보다 이전인 경우
                            if(pstngStartDate > pstngEndDateMod) {
                                mvno.ui.alert( "요금제코드["+ rateCd +"]의 <br>변경적용종료일시[" + pstngEndDateMod + "]<br> 기존적용시작일시["  + pstngStartDate + "]<br> 이전으로 변경 할 수 없습니다.");
                                return;
                            }
                            //변경할 적용종료일자가 현재보다 이전인 경우
                            if(pstngEndDateMod < now) {
                                mvno.ui.alert( "요금제코드["+ rateCd +"]의 <br>변경적용종료일시[" + pstngEndDateMod + "]<br> 현재 [" + now + "]<br> 이전으로 변경 할 수 없습니다.");
                                return;
                            }
                            //** 최신으로 등록된 요금제코드가 아닌경우
                            if(newestYn != "Y" ){
                                 //기존 적용종료일자보다 변경할 적용종료일자가 이후인 경우
                                if(pstngEndDateMod > pstngEndDate) {
                                    mvno.ui.alert("요금제코드["+ rateCd +"]의 <br>변경적용종료일시[" +pstngEndDateMod+ "]<br> 기존적용종료일시[" +pstngEndDate+ "]<br> 이후로 변경할 수 없습니다.");
                                    return;
                                }
                            }
                            jsonObj = {
                                rateCd : rateCd
                                ,pstngStartDate : pstngStartDate
                                ,pstngEndDate : pstngEndDate
                                ,pstngEndDateMod : pstngEndDateMod
                                ,newestYn : newestYn
                                ,flag : "L"
                            }
                            arrObj.push(jsonObj);

                        };

                        var sData = { items : arrObj };

                        mvno.ui.confirm("선택한 요금제코드의 종료일시를 변경 하시겠습니까?",function() {
                            mvno.cmn.ajax4json(ROOT + "/sale/rateMgmt/updRateAcenListEndDateMod.json", sData, function(result) {
                                if(result.result.code == "OK"){
                                    PAGE.GRID1.refresh();
                                    mvno.ui.notify("NCMN0002");
                                    mvno.ui.alert("NCMN0002");
                                    mvno.ui.closeWindowById("FORM3");
                                }
                            });
                        })
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
            mvno.ui.createGrid("GRID1");
            mvno.ui.createGrid("GRID2");

            PAGE.FORM1.setItemValue("stdrDt", today);
        }
    };

</script>
